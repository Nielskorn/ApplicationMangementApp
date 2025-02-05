import {Application} from "../types/Application.ts";
import {useNavigate} from "react-router-dom";
import "../App.css"
type jobApplicationCardProps={
    application:Application;
}

export default function ApplicationCard({application}: Readonly<jobApplicationCardProps>){
    const navigate=useNavigate();
    function navigateToDetailspage() {
        navigate("/application/" + application.id);
    }


    return(
        <div className="applicationCard" onClick={navigateToDetailspage} onKeyDown={navigateToDetailspage}>
            <h2>{application.jobOfferID}</h2>
            <p>{"Status="+application.applicationStatus}</p>
        </div>
    )
}