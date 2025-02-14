import {Application} from "../types/Application.ts";
import {useNavigate} from "react-router-dom";
import "../App.css"
import StatusIndicator from "./StatusIndicator.tsx";

type JobApplicationCardProps = {
    application: Application;
}

export default function ApplicationCard({application}: Readonly<JobApplicationCardProps>) {
    const navigate = useNavigate();

    function navigateToDetailspage() {
        navigate("/application/" + application.id);
    }


    return (
        <div className="applicationCard">
            <h2>{application.jobOfferID}</h2>
            <p>status: <StatusIndicator status={application.applicationStatus}/></p>
            <button onClick={navigateToDetailspage}>Go to Details</button>
        </div>
    )
}
