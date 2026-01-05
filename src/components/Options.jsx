import { NavLink } from "react-router";
import style from "./Options.module.css"

const Options = ({loggedIn, role}) => {
    let products = null
    let basket = null

if(loggedIn){
    if(role === "User"){
        products = <NavLink to="products" end>Products</NavLink>
        basket = <NavLink to="basket" end>Basket</NavLink>
    } 
    if(role === "Admin"){
        products = <NavLink to="productsAdmin" end>Products</NavLink>
    }
}

    return (
        <>
        <div className={style.options}>
        <NavLink to="/" end>Home</NavLink>
        {products}
        {basket}
        </div>
       </>
    )
}

export default Options;