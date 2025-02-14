import axios from "axios";

import {useEffect, useState} from "react";
import "/src/App.css"

import {JobApplicationTracker} from "../types/JobApplicationTracker.ts";
import JobApplicationCard from "./JobApplicationCard.tsx";

export function DashViewApplication() {
    const [ApplicationTrackerData, setApplicationTrackerData] = useState<JobApplicationTracker[]>([])

    function getApplications() {
        axios.get<JobApplicationTracker[]>("/api/JobApplication/dash").then(
            (response) => {
                setApplicationTrackerData(response.data)
            }
        )
    }

    const applicationCards = ApplicationTrackerData.map((application) => (
        <li className="JobapplicationCard" key={application.application.id}>
            <JobApplicationCard JobApplication={application}/>
        </li>));

    useEffect(() => {
        getApplications()

    }, []);
    return (
        <ul className="ulWithNoBullets">
            {applicationCards}
        </ul>
    )
}
