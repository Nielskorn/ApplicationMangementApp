import {useEffect, useState} from "react";
import {JobApplicationTracker} from "../types/JobApplicationTracker.ts";
import axios from "axios";
import JobApplicationCard from "./JobApplicationCard.tsx";

export default function JobApplicationGallary() {
    const [jobApplicationData, setJobApplicationData] = useState<JobApplicationTracker[]>([])

    function getApplications() {
        axios.get<JobApplicationTracker[]>("/api/JobApplication/all").then(
            (response) => {
                setJobApplicationData(response.data)
            }
        )
    }

    const applicationCards = jobApplicationData.map((application) => (
        <li className="JobapplicationCard" key={application.application.id}>
            <JobApplicationCard JobApplication={application}/>
        </li>));

    useEffect(() => {
        getApplications()

    }, []);
    return (
        <div>
            <ul className="ulWithNoBullets">
                {applicationCards}
            </ul>
        </div>
    )

}
