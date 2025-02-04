
import {JobApplicationTracker} from "../types/JobApplicationTracker.ts";

type jobApplicationCardProps={
    JobApplication:JobApplicationTracker;
}
export default function JobApplicationCard({JobApplication}:Readonly<jobApplicationCardProps>){
    return(
        <div>
        <h2>
            {JobApplication.jobOffer.jobTitle}
        </h2>
        <h3>By {JobApplication.jobOffer.companyName}
        </h3>
            <p>Next Reminder Date: {JobApplication.application.reminderTime}</p>
        </div>
    )
}