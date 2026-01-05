import { useEffect, useState } from "react"
import { useOutletContext } from "react-router"
import styles from "./ProductsAdmin.module.css";


const ProductsAdmin = () => {
    const {APIURL} = useOutletContext()
    const APIExtensionProducts = "/products"
    const [products, setProducts] = useState([])
    const [refreshCounter, setRefreshCounter] = useState(0)
    const token = localStorage.getItem("token")

    const [name, setName] = useState("")
    const [price, setPrice] = useState(0)
    const [category, setCategory] = useState("")


    useEffect(() =>{
        fetch(APIURL+APIExtensionProducts, {
            method: "GET",
            headers: {
            'Content-Type': 'application/json',
            'Authorization': "Bearer " + token
        }
        })
        .then((res) => {
            if(res.ok){
                return res.json()
            }
        })
        .then((data) => {
            console.log(data)
            setProducts(data.productDTOS)})
        .catch((error) => console.log("An error has occured when trying to get products: ", error))
    }, [APIURL, token, refreshCounter])

    function handleEdit(id){
        fetch(APIURL+APIExtensionProducts+"/"+id, {
            method: "PUT",
            headers: {
            'Content-Type': 'application/json',
            'Authorization': "Bearer " + token
        },
        body: JSON.stringify({
            name: name,
            price: price,
            category: category
        })})
        .then((response) => {
            if(!response.ok) throw new Error("An error has occured when trying to update")
            return response.json()
        })
        .then((data) => {
            console.log("Product has been updated: ", data)
            alert("Product has been updated!")
            setRefreshCounter(prev => prev+1)
        })
        .catch((error) => console.error("Fetch error has occured: ", error))
    }

    async function handleDelete(id){
        try {
        const response = await fetch(APIURL+APIExtensionProducts+"/"+id, {
            method: "DELETE",
            headers: {
            'Content-Type': 'application/json',
            'Authorization': "Bearer " + token
        }})
            if(!response.ok) throw new Error("Error has occured when trying to delete")
            const data = await response.text()
      
            alert("Product has been deleted: ", data)
            setRefreshCounter(prev => prev+1)
        }catch(error){
            console.log("An error has occured: ", error.message)
        }
        
    }

    function handleAdd(){
        fetch(APIURL+APIExtensionProducts, {
            method: "POST",
            headers: {
            'Content-Type': 'application/json',
            'Authorization': "Bearer " + token
        },
        body: JSON.stringify({
            name: name,
            price: price,
            category: category
        })})
        .then((response) => {
            if(!response.ok){
                throw new Error('HTTP error! Status code: ', response.status)
            }
            return response.json()
        })
        .then((data) => {
            console.log('New Product Added: ', data)
            alert("Product has been added")
            setRefreshCounter(prev => prev+1)
        })
        .catch((error) => console.error('Fetch error: ', error))
    }
   
    return (
        <>
        <div className={styles.container}>
        
        <h1>Add new product</h1>
        
        <div className={styles.form}>
        <label>Enter name</label>
        <input type="text" name="productName" onChange={(e) => setName(e.target.value)}/>
        <label>Enter price</label>
        <input type="number" name="price" min={0} onChange={(e) => setPrice(e.target.value)}/>
        <select onChange={(e) => setCategory(e.target.value)}>
            <option name="" value=""></option>
            <option name="Food" value="FOOD">Food</option>
            <option name="Electronics" value="ELECTRONICS">Electronics</option>
            <option name="Furniture" value="FURNITURE">Furniture</option>
        </select>
        <button type="button" onClick={handleAdd}>Add product</button>
        </div>

        <h1>Products</h1>
        <p>If you want to update a product we will replace the selected product with information entered into the add product fields</p>
        {products.map((product) => (
            <div key={product.id} className={styles.product}>
            <p>{product.name}</p>
            <p>{product.price}</p>
            <p>{product.category}</p>
            <button type="button" onClick={() => handleEdit(product.id)}>Update</button>
            <button type="button" onClick={() => handleDelete(product.id)}>Delete</button>
            </div>
        ))}
        </div>
        </>
    )

}

export default ProductsAdmin