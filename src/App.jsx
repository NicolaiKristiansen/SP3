import { useState } from 'react'
import Login from './components/Login'
import Options from './components/Options'
import './App.css'
import { Navigate, Outlet } from 'react-router'

function App() {
  const APIURL = "webshop.knoglemau.dk/api/";
  const [role, setRole] = useState("")
  const [loggedIn, setLoggedIn] = useState(false)
  const basketId = 1

  return (
    <>
     <Login role={role} setRole={setRole} loggedIn={loggedIn} setLoggedIn={setLoggedIn} APIURL={APIURL}/>
     <Options loggedIn={loggedIn} role={role}/>
     <Outlet context={{APIURL, loggedIn, basketId}}/>
    </>
  )
}

export default App
