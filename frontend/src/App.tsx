import NewApplicationSite from "./Page/NewApplicationSite.tsx";



import './App.css'

import {Link, Route, Routes} from "react-router-dom";
import Applicationdetails from "./Page/ApplicationDetails.tsx";
import HomePage from "./Page/HomePage.tsx";
import EditApplicationSite from "./Page/EditApplicationSite.tsx";
import JobOfferPage from "./Page/JobOfferPage.tsx";
import EditJobOfferPage from "./Page/EditJobOfferPage.tsx";
import NewJobOfferPage from "./Page/NewJobOffer.tsx";

function App() {


  return (
    <>
<header>
    <nav>
    <Link to="/">Home </Link>
        <Link to="/newApplication">newApplycation </Link>
        <Link to="jobOffer"> JobOffers</Link>

    </nav>
</header>
        <main className="container">
        <Routes>
            <Route path="/application/:id" element={<Applicationdetails/>} />
            <Route path="/" element={<HomePage/>}/>
            <Route path="/jobOffer" element={<JobOfferPage/>}/>
            <Route path="/newJobOffer" element={<NewJobOfferPage/>}/>
            <Route path="/editJobOffer/:id" element={<EditJobOfferPage/>} />
            <Route path="/newApplication" element={<NewApplicationSite/>}/>
            <Route path="/editApplication/:id" element={<EditApplicationSite/>} />
        </Routes>
        </main>
        <footer>
            <p>All rights reserved</p>
        </footer>
    </>
  )
}

export default App
