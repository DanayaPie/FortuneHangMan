import { Outlet } from "react-router-dom";
// import NavBar from "../Components/NavBar";

function Root() {
    return (
        <>
            {/* <NavBar /> */}
            <Outlet />
        </>
    )
}
export default Root;