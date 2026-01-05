import { StrictMode } from 'react'
import ReactDOM from "react-dom/client";
import './index.css'
import { BrowserRouter, Routes, Route } from "react-router";
import App from './App.jsx'
import Products from './components/USER/Products.jsx';
import Home from './components/Home.jsx';
import ProtectedRoutes from './components/ProtectedRoutes.jsx';
import Basket from './components/USER/Basket.jsx';
import ProductsAdmin from './components/ADMIN/ProductsAdmin.jsx';

const root = document.getElementById('root')

ReactDOM.createRoot(root).render(
  <BrowserRouter>
    <Routes>
      <Route path='/' element={<App />}>
      <Route index element={<Home />}/>
      <Route element={<ProtectedRoutes />}>
      <Route path='products' element={<Products />} />
      <Route path='basket' element={<Basket />}/>
      <Route path='productsAdmin' element={<ProductsAdmin />}/>
      </Route>
      </Route>
    </Routes>
  </BrowserRouter>,
)
