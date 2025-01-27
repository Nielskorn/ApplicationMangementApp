import NewApplicationSite from "./Page/NewApplicationSite.tsx";



import './App.css'

import {Link, Route, Routes} from "react-router-dom";
import Applicationdetails from "./Page/ApplicationDetails.tsx";
import HomePage from "./Page/HomePage.tsx";

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
        </Routes>
    </>
  )
}

export default App
