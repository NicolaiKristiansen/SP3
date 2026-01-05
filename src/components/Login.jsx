import { useState } from "react";
import styles from "./Login.module.css"

const Login = ({APIURL, loggedIn, setLoggedIn, setRole}) => {
    const login = "/auth/login"
    const [username, setUsername] = useState("")
    const [password, setPassword] = useState("")

    function handleLogin(e){
        e.preventDefault()
        console.log(username," + ", password)
        
        fetch(APIURL + login, {
            method: "POST",
            headers: {
                'Content-Type': 'application/json', 

            },
            body: JSON.stringify({
                username,
                password
            })
        })
        .then((response) => response.json())
        .then((data) => {
            localStorage.setItem("token", data.token)
            setLoggedIn(true)
            getRole()
        })
        .catch((error) => {
            setLoggedIn(false)
            alert("This account does not exist")
            console.log("An error has occured:", error.message)})
    }

    function getRole(){
        const token = localStorage.getItem("token")
        if(!token) return null;

        const payload = token.split(".")[1];
        const payloadJSON = atob(payload)
        const json = JSON.parse(payloadJSON)
        console.log("Role", json.roles)
        setRole(json.roles)
    }
    
    return (
        <>
        {loggedIn ?
        <div className={styles.login}>
            <p>{username}</p>
            <button type="submit" onClick={() => {
                setLoggedIn(false)
                localStorage.removeItem("token")
            }}>Log out</button>
        </div>
        : <form onSubmit={handleLogin} className={styles.login}>
        <input name="username" type="text" placeholder="username" required onChange={(e) => setUsername(e.target.value)}/>
        <input name="password" type="password" placeholder="password" required onChange={(e) => setPassword(e.target.value)}/> 
        <button type="submit">Login</button>
        </form>}
        </>
    )
}
export default Login;