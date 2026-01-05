import { useEffect, useState } from "react";
import { Outlet, useOutletContext } from "react-router";
import styles from "./products.module.css"

const Products = () => {
    const {APIURL, basketId} = useOutletContext();
    const GETPRODUCTS = "/products"
    const ADDTOBASKET = "/baskets"
    const [products, setProducts] = useState([])
    const [sort, setSort] = useState("")
    const token = localStorage.getItem("token")

    useEffect(() => {
        fetch(APIURL + GETPRODUCTS, {
        method: "GET",
        headers: {
            'Content-Type': 'application/json',
            'Authorization': "Bearer " + token
        }
    })
    .then((response) => response.json())
    .then((data) =>  setProducts(data.productDTOS))
}, [APIURL, token, sort])

function handleAdd(event, product){
    event.preventDefault();
    const amount = parseInt(event.target.amount.value);

    fetch(APIURL + ADDTOBASKET, {
        method: "POST",
        headers: {
            'Content-type': 'application/json',
            'Authorization': 'Bearer ' + token
        },
        body: JSON.stringify({
            basketId,
            productId: product.id,
            amount
        })
    })
    .then(data => console.log("Added:", data))
    .catch((err) => console.log("An error has occured in the handleAdd function: ", err));

}

const filteredProducts = sort ? 
    products.filter((product) => product.category === sort) : products
   
    

    return (
        <>
        <div className={styles.container}>
        <h1>Velkommen her kan du se hvilke produkter vi har at tilbyde</h1>
        <select onChange={(e) => setSort(e.target.value)}>
            <option name="" value="">All options</option>
            <option name="ELECTRONICS" value="ELECTRONICS">ELECTRONICS</option>
            <option name="FOOD" value="FOOD">FOOD</option>
            <option name="FURNITURE" value="FURNITURE">FURNITURE</option>
        </select>
        {filteredProducts.map((product) => (
            <form key={product.id} onSubmit={(e) => handleAdd(e, product)}>
            <div>
                <p>{product.name}</p>
                <p>{product.price}</p>
                <p>{product.category}</p>
                <br></br>
                <input name="amount" type="number" placeholder="0" min={0}/>
                <button type="submit" placeholder="Add">Add</button>
            </div>
            </form>))}
            </div>
        </>
    )
}

export default Products;