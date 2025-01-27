import NewApplicationSite from "./Page/NewApplicationSite.tsx";



import './App.css'

import {Link, Route, Routes} from "react-router-dom";
import Applicationdetails from "./Page/ApplicationDetails.tsx";
import HomePage from "./Page/HomePage.tsx";
import EditApplicationSite from "./Page/EditApplicationSite.tsx";

function App() {


  return (
    <>
<header>
    <nav>
    <Link to="/">Home </Link>
        <Link to="/newApplication">newApplycation </Link>

    </nav>
</header>
        <Routes>
            <Route path="/application/:id" element={<Applicationdetails/>} />
            <Route path="/"element={<HomePage/>}/>
            <Route path="/newApplication" element={<NewApplicationSite/>}/>
        <Route path="/editApplication/:id" element={<EditApplicationSite/>} />
        </Routes>
    </>
  )
}

export default App
