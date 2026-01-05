import { useEffect, useState } from "react"
import { useOutletContext, useNavigate } from "react-router"
import styles from "./Basket.module.css";


const Basket = () => {
    const {APIURL, basketId} = useOutletContext();
    const navigate = useNavigate();
    const APIExtension = "/basketproducts/"
    const APIExtensionReceipt = "/receipt/"
    
    const token = localStorage.getItem("token")
    const [basket, setBasket] = useState([])

useEffect(() => {
    fetch(APIURL+APIExtension+basketId, {
        method: "GET",
        headers: {
            'Content-Type': 'application/json',
            'Authorization': "Bearer " + token
        }
    })
    .then((response) => response.json())
    .then((data) => setBasket(data.basketProductDTOList))
}, [APIURL, token, basketId])

function handleBuy(){
    const APIExtensionBuy = "/baskets/buy/"
    console.log(basketId)

    fetch(APIURL+APIExtensionBuy+basketId, {
        method: "POST"})
        .then((res) => {
            if(!res.ok) throw new Error("Purchase failed");
            return res.text()
        })
        .then((message) => {
            console.log(message)
            setBasket([])
            navigate("/", {replace: true})
            
        })
        .catch((error) => console.log("An error has occured when buying basket: ", error.message))
}
    

    return (
        <>
        <div className={styles.container}>
        <table>
            <tbody>
                <tr>
                    <th>Name</th>
                    <th>Amount</th>
                    <th>Price</th>
                </tr>
            {basket.map((product) => (
                <tr key={product.product}>
                    <th>{product.productName}</th>
                    <th>{product.amount}</th>
                    <th>{product.price}</th>
                    {/* This is not a true delete, since we do not have a route for it in the backend*/}
                    <th><button type="submit" onClick={() => setBasket((prev) => prev.filter((p) => p.product !== product.product))}>Delete</button></th>
                </tr>
            ))}
            </tbody>
        </table>
        <button type="button" onClick={handleBuy}>Buy</button>
        </div>
        </>
    )
}

export default Basket;