import { Navigate, Outlet, useOutletContext } from "react-router";

const ProtectedRoutes = () => {
    const { loggedIn } = useOutletContext();
    const { APIURL } = useOutletContext();
    const { basketId } = useOutletContext();

    if(!loggedIn){
        return <Navigate to="/" replace />
    }

    return <Outlet context={{APIURL, basketId, loggedIn}}/>
}

export default ProtectedRoutes;