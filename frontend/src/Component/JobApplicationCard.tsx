import {JobApplicationTracker} from "../types/JobApplicationTracker.ts";
import StatusIndicator from "./StatusIndicator.tsx";
import {useNavigate} from "react-router-dom";

type jobApplicationCardProps = {
    JobApplication: JobApplicationTracker;
}
export default function JobApplicationCard({JobApplication}: Readonly<jobApplicationCardProps>) {

    const navigate = useNavigate();

    function navigateToDetailsPage() {
        navigate("/jobapplication/" + JobApplication.application.id);
    }

    return (<div>
            {JobApplication.jobOffer ? (<div><h2>
                {JobApplication.jobOffer.jobTitle}
            </h2>
                <h3>By {JobApplication.jobOffer.companyName}
                </h3>
            </div>) : (<div>
                <h2>Application ID: {JobApplication.application.id}</h2>
            </div>)
            }
            <p>Next Reminder Date: {JobApplication.application.reminderTime}</p>
            status: <StatusIndicator status={JobApplication.application.applicationStatus}/>
            <button onClick={navigateToDetailsPage}>Go to Details</button>
        </div>
    )
}