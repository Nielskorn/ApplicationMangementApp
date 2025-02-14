import NewApplicationSite from "./Page/NewApplicationSite.tsx";


import './App.css'

import {Route, Routes} from "react-router-dom";
import Applicationdetails from "./Page/ApplicationDetails.tsx";
import HomePage from "./Page/HomePage.tsx";
import EditApplicationSite from "./Page/EditApplicationSite.tsx";
import JobOfferPage from "./Page/JobOfferPage.tsx";
import EditJobOfferPage from "./Page/EditJobOfferPage.tsx";
import NewJobOfferPage from "./Page/NewJobOffer.tsx";
import JobDetailsPage from "./Page/JobDetailsPage.tsx";
import {ApplicationPage} from "./Page/ApplicationPage.tsx";
import Header from "./Component/Header.tsx";
import JobApplicationDetails from "./Page/JobApplicationDetails.tsx";


function App() {


    return (
        <>
            <header className="header">
                <h1 className="h1">Application Management</h1>
                <Header/>
            </header>
            <main className="container">
                <Routes>
                    <Route path={"/"} element={<HomePage/>}/>
                    <Route path="/application/:id" element={<Applicationdetails/>}/>
                    <Route path="/jobOffer/:id" element={<JobDetailsPage/>}/>
                    <Route path="/application" element={<ApplicationPage/>}/>
                    <Route path="/jobOffer" element={<JobOfferPage/>}/>
                    <Route path="/newJobOffer" element={<NewJobOfferPage/>}/>
                    <Route path="/editJobOffer/:id" element={<EditJobOfferPage/>}/>
                    <Route path="/newApplication" element={<NewApplicationSite/>}/>
                    <Route path="/newApplication/:id" element={<NewApplicationSite/>}/>
                    <Route path="/editApplication/:id" element={<EditApplicationSite/>}/>
                   
                    <Route path="/jobApplication/:id" element={<JobApplicationDetails/>}/>
                </Routes>
            </main>
            <footer>
                <p>All rights reserved</p>
            </footer>
        </>
    )
}

export default App
