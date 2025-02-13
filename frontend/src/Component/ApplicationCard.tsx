import {Application} from "../types/Application.ts";
import {useNavigate} from "react-router-dom";
import "../App.css"
import StatusIndicator from "./StatusIndicator.tsx";

type jobApplicationCardProps = {
    application: Application;
}

export default function ApplicationCard({application}: Readonly<jobApplicationCardProps>) {
    const navigate = useNavigate();

    function navigateToDetailspage() {
        navigate("/application/" + application.id);
    }


    return (
        <div className="applicationCard">
            <h2>{application.jobOfferID}</h2>
            status: <StatusIndicator status={application.applicationStatus}/>
            <button onClick={navigateToDetailspage}>Go to Details</button>
        </div>
    )
}