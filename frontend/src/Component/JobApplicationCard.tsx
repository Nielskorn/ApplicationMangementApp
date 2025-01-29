import {Application} from "../types/Application.ts";
import {useNavigate} from "react-router-dom";

type jobApplicationCardProps={
    application:Application;
}

export default function JobApplicationCard({application}: Readonly<jobApplicationCardProps>){
    const navigate=useNavigate();
    function navigateToDetailspage() {
        navigate("/application/" + application.id);
    }


    return(
        <div onClick={navigateToDetailspage} onKeyDown={navigateToDetailspage}>
            <h2>{application.jobOfferID}</h2>
            <p>{"Status="+application.appliStatus}</p>
        </div>
    )
}