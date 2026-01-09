// NewsMate — client-side demo with lightweight personalization
const sampleArticles = [
  {id:1, title:'AI models reach new efficiency milestones', cat:'tech', source:'TechDaily', date:'2025-12-10', img:'https://images.unsplash.com/photo-1518770660439-4636190af475?auto=format&fit=crop&w=800&q=60', snippet:'Researchers reduce model size while keeping accuracy high.','body':'Full article text: AI model advancements allow...','likes':12,'dislikes':2},
  {id:2, title:'Global markets react to new trade talks', cat:'business', source:'MarketWatch', date:'2025-12-11', img:'https://images.unsplash.com/photo-1542223616-7d4e7a9c3c2f?auto=format&fit=crop&w=800&q=60', snippet:'Stocks surged following agreements between nations.','body':'Full article text: Markets responded...','likes':8,'dislikes':1},
  {id:3, title:'Historic win in world cup qualifier', cat:'sports', source:'SportsNet', date:'2025-12-09', img:'https://images.unsplash.com/photo-1517649763962-0c623066013b?auto=format&fit=crop&w=800&q=60', snippet:'An unexpected victory electrified fans worldwide.','body':'Full article text: A dramatic match...','likes':20,'dislikes':3},
  {id:4, title:'New climate policy proposed by coalition', cat:'world', source:'GlobalNews', date:'2025-12-12', img:'https://images.unsplash.com/photo-1504691342899-9d0b0101c34e?auto=format&fit=crop&w=800&q=60', snippet:'Governments discuss new emissions targets.','body':'Full article text: Leaders proposed...','likes':6,'dislikes':0},
  {id:5, title:'Indie film festival highlights rising stars', cat:'entertainment', source:'FilmScope', date:'2025-12-08', img:'https://images.unsplash.com/photo-1517604931442-7f2b1d3d6a6b?auto=format&fit=crop&w=800&q=60', snippet:'Critics praise innovative storytelling.','body':'Full article text: The festival showcased...','likes':4,'dislikes':0},
  {id:6, title:'Breakthrough battery tech promises longer range', cat:'tech', source:'EnergyToday', date:'2025-12-13', img:'https://images.unsplash.com/photo-1542831371-d531d36971e6?auto=format&fit=crop&w=800&q=60', snippet:'A small change in chemistry yields major gains.','body':'Full article text: Battery researchers...','likes':14,'dislikes':1}
];

const state = {
  articles: sampleArticles,
  users: {},        // loaded from localStorage
  activeUserId: null,
  filter: 'all',
  sort: 'personal',
  searchQ: '',
  activeArticle: null
};

function $(sel){return document.querySelector(sel)}
function loadState(){
  const saved = localStorage.getItem('newsmate_state_v1')
  if(saved) {
    try { Object.assign(state, JSON.parse(saved)) } catch(e){}
  }
  const usersRaw = localStorage.getItem('newsmate_users_v1')
  if(usersRaw) state.users = JSON.parse(usersRaw)
  // ensure sample user exists
  if(!Object.keys(state.users).length){
    const id = createUser('Guest')
    state.activeUserId = id
    saveUsers()
  }
  if(!state.activeUserId) state.activeUserId = Object.keys(state.users)[0]
}
function saveUsers(){ localStorage.setItem('newsmate_users_v1', JSON.stringify(state.users)) }
function persistState(){ localStorage.setItem('newsmate_state_v1', JSON.stringify({filter:state.filter, sort:state.sort, searchQ:state.searchQ})) }

// User management
function createUser(name){
  const id = 'u_' + Date.now()
  state.users[id] = {id, name, likes:{}, dislikes:{}, saved:[], theme:'light'}
  saveUsers()
  renderUserSelect()
  return id
}
function setActiveUser(id){
  state.activeUserId = id
  renderProfileSummary()
  renderArticles()
  updateSavedUI()
  applyTheme()
}

// Personalization: compute score per article by user's liked categories count
function computePersonalScores(){
  const user = state.users[state.activeUserId]
  if(!user) return {}
  const catCounts = {}
  for(const aid of Object.keys(user.likes || {})){
    const art = state.articles.find(a=>a.id==aid)
    if(art) catCounts[art.cat] = (catCounts[art.cat]||0) + 1
  }
  const scores = {}
  state.articles.forEach(a=>{
    const base = 0
    const catBoost = catCounts[a.cat] ? Math.log(1 + catCounts[a.cat]) : 0
    const popularity = Math.log(1 + (a.likes||0))
    scores[a.id] = base + catBoost * 2 + popularity * 0.6
  })
  return scores
}

// Rendering
function renderUserSelect(){
  const sel = $('#userSelect'); sel.innerHTML = ''
  Object.values(state.users).forEach(u=>{
    const opt = document.createElement('option'); opt.value = u.id; opt.textContent = u.name
    if(u.id === state.activeUserId) opt.selected = true
    sel.appendChild(opt)
  })
}
function renderProfileSummary(){
  const info = $('#profileInfo')
  const u = state.users[state.activeUserId]
  if(!u){ info.textContent = '— no user —'; return }
  const liked = Object.keys(u.likes||{}).length
  const disliked = Object.keys(u.dislikes||{}).length
  const saved = (u.saved||[]).length
  info.innerHTML = `<div><strong>${u.name}</strong></div>
  <div class="meta">${liked} liked · ${disliked} disliked · ${saved} saved</div>`
  $('#savedCount').textContent = saved
}
function renderArticles(){
  let list = state.articles.slice()
  // search
  if(state.searchQ){
    const q = state.searchQ.toLowerCase()
    list = list.filter(a => a.title.toLowerCase().includes(q) || a.snippet.toLowerCase().includes(q) || a.source.toLowerCase().includes(q))
  }
  // category filter
  if(state.filter && state.filter!=='all') list = list.filter(a=>a.cat===state.filter)
  // personalization / sort
  if(state.sort==='personal'){
    const scores = computePersonalScores()
    list.sort((x,y)=> (scores[y.id]||0) - (scores[x.id]||0) )
  } else if(state.sort==='latest'){
    list.sort((x,y)=> new Date(y.date) - new Date(x.date))
  } else if(state.sort==='popular'){
    list.sort((x,y)=> (y.likes||0) - (x.likes||0))
  }

  const grid = $('#articleGrid'); grid.innerHTML = ''
  list.forEach(a=>{
    const el = document.createElement('article'); el.className = 'card'; el.setAttribute('role','listitem')
    const user = state.users[state.activeUserId] || {}
    const liked = user.likes && user.likes[a.id]
    const disliked = user.dislikes && user.dislikes[a.id]
    el.innerHTML = `
      <img src="${a.img}" alt="${a.title}" />
      <div>
        <h4>${a.title}</h4>
        <div class="meta">${a.source} · ${a.date} · <em>${a.cat}</em></div>
        <p class="snippet">${a.snippet}</p>
      </div>
      <div class="actions">
        <div>
          <button class="like" data-id="${a.id}" aria-pressed="${!!liked}">${liked? '👍' : '👍' } <span>${a.likes||0}</span></button>
          <button class="dislike" data-id="${a.id}" aria-pressed="${!!disliked}">${disliked? '👎' : '👎' } <span>${a.dislikes||0}</span></button>
        </div>
        <div>
          <button class="secondary" data-id="${a.id}" data-action="read">Read</button>
          <button class="primary" data-id="${a.id}" data-action="save">Save</button>
        </div>
      </div>
    `
    grid.appendChild(el)
  })
}

// Modal / article view
function openArticle(a){
  state.activeArticle = a
  $('#modalImg').src = a.img
  $('#modalTitle').textContent = a.title
  $('#modalSource').textContent = a.source
  $('#modalDate').textContent = a.date
  $('#modalBody').textContent = a.body || a.snippet
  $('#likeCount').textContent = a.likes||0
  $('#dislikeCount').textContent = a.dislikes||0
  $('#articleModal').classList.remove('hidden')
}
function closeModal(){ state.activeArticle = null; $('#articleModal').classList.add('hidden') }

// Likes / dislikes / save
function toggleLike(aid){
  const user = state.users[state.activeUserId]
  if(!user) return
  user.likes = user.likes || {}
  user.dislikes = user.dislikes || {}
  if(user.likes[aid]){
    delete user.likes[aid]; // un-like
    const art = state.articles.find(a=>a.id==aid); if(art && art.likes) art.likes = Math.max(0, art.likes-1)
  } else {
    user.likes[aid] = Date.now()
    const art = state.articles.find(a=>a.id==aid); if(art) art.likes = (art.likes||0)+1
    if(user.dislikes[aid]) { delete user.dislikes[aid]; const art2 = state.articles.find(a=>a.id==aid); if(art2 && art2.dislikes) art2.dislikes = Math.max(0, art2.dislikes-1)}
  }
  saveUsers(); renderProfileSummary(); renderArticles(); updateSavedUI()
}
function toggleDislike(aid){
  const user = state.users[state.activeUserId]
  if(!user) return
  user.dislikes = user.dislikes || {}
  user.likes = user.likes || {}
  if(user.dislikes[aid]){
    delete user.dislikes[aid]; const art = state.articles.find(a=>a.id==aid); if(art && art.dislikes) art.dislikes = Math.max(0, art.dislikes-1)
  } else {
    user.dislikes[aid] = Date.now()
    const art = state.articles.find(a=>a.id==aid); if(art) art.dislikes = (art.dislikes||0)+1
    if(user.likes[aid]) { delete user.likes[aid]; const art2 = state.articles.find(a=>a.id==aid); if(art2 && art2.likes) art2.likes = Math.max(0, art2.likes-1)}
  }
  saveUsers(); renderProfileSummary(); renderArticles(); updateSavedUI()
}
function toggleSave(aid){
  const user = state.users[state.activeUserId]; if(!user) return
  user.saved = user.saved || []
  const idx = user.saved.indexOf(aid)
  if(idx>=0) user.saved.splice(idx,1)
  else user.saved.unshift(aid)
  saveUsers(); renderProfileSummary(); updateSavedUI(); renderArticles()
}
function updateSavedUI(){
  const panel = $('#savedPanel')
  const cont = $('#savedItems'); cont.innerHTML = ''
  const user = state.users[state.activeUserId] || {saved:[]}
  const savedIds = user.saved || []
  savedIds.forEach(id=>{
    const a = state.articles.find(x=>x.id==id)
    if(!a) return
    const row = document.createElement('div'); row.className='cart-item'
    row.innerHTML = `<img src="${a.img}" /><div style="flex:1"><strong>${a.title}</strong><div class="meta">${a.source} · ${a.date}</div></div><div><button data-id="${a.id}" class="secondary" data-action="remove">Remove</button></div>`
    cont.appendChild(row)
  })
  $('#savedCount').textContent = savedIds.length
}

// UI events
window.addEventListener('click', e=>{
  if(e.target.matches('[data-action="read"]') || e.target.closest('[data-action="read"]')){
    const id = +e.target.dataset.id || +e.target.closest('[data-id]')?.dataset.id
    const a = state.articles.find(x=>x.id==id); if(a) openArticle(a)
  }
  if(e.target.matches('[data-action="save"]') || e.target.closest('[data-action="save"]')){
    const id = +e.target.dataset.id; toggleSave(id)
  }
  if(e.target.matches('.like')){ const id=+e.target.dataset.id; toggleLike(id) }
  if(e.target.matches('.dislike')){ const id=+e.target.dataset.id; toggleDislike(id) }
  if(e.target.matches('#modalClose')) closeModal()
  if(e.target.matches('#savedToggle')) {
    const panel = $('#savedPanel'); panel.classList.toggle('hidden'); panel.setAttribute('aria-hidden', panel.classList.contains('hidden'))
  }
  if(e.target.matches('#clearSaved')) {
    const u = state.users[state.activeUserId]; if(u){ u.saved = []; saveUsers(); updateSavedUI(); renderProfileSummary() }
  }
  if(e.target.matches('#openSource')) {
    alert('This demo does not open external links. In a real app this would open the article URL.')
  }
  if(e.target.matches('.secondary[data-action="remove"]')) {
    const id=+e.target.dataset.id; const u = state.users[state.activeUserId]; if(u){ const i=u.saved.indexOf(id); if(i>=0) u.saved.splice(i,1); saveUsers(); updateSavedUI(); renderProfileSummary(); renderArticles() }
  }
})

// inputs
$('#searchInput').addEventListener('input', e=>{ state.searchQ = e.target.value; renderArticles(); persistState() })
document.querySelectorAll('.catBtn').forEach(b=>b.addEventListener('click', e=>{ state.filter = e.target.dataset.cat; renderArticles(); persistState() }))
$('#sortSelect').addEventListener('change', e=>{ state.sort = e.target.value; renderArticles(); persistState() })

// user actions
$('#newUserBtn').addEventListener('click', ()=>{
  const name = prompt('Enter new user name')
  if(name){ const id = createUser(name); setActiveUser(id); saveUsers() }
})
$('#userSelect').addEventListener('change', e=>{ setActiveUser(e.target.value) })

// theme
function applyTheme(){
  const u = state.users[state.activeUserId] || {}
  const theme = u.theme || 'light'
  document.documentElement.setAttribute('data-theme', theme==='dark' ? 'dark' : 'light')
  $('#themeToggle').textContent = theme==='dark' ? '☀️' : '🌙'
  $('#themeToggle').setAttribute('aria-pressed', theme==='dark')
}
$('#themeToggle').addEventListener('click', ()=>{
  const u = state.users[state.activeUserId]; if(!u) return
  u.theme = (u.theme==='dark') ? 'light' : 'dark'; saveUsers(); applyTheme()
})

// recommendations panel
$('#viewRec').addEventListener('click', ()=>{
  state.sort = 'personal'; $('#sortSelect').value = 'personal'; renderArticles()
})

// init
function init(){
  loadState()
  renderUserSelect()
  renderProfileSummary()
  renderArticles()
  updateSavedUI()
  applyTheme()
}
init()