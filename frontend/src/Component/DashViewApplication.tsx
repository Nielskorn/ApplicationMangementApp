import axios from "axios";

import {useEffect, useState} from "react";
import "/src/App.css"

import {JobApplicationTracker} from "../types/JobApplicationTracker.ts";
import JobApplicationCard from "./JobApplicationCard.tsx";

export function DashViewApplication() {
    const [odata, setOdata] = useState<JobApplicationTracker[]>([])

    function getApplications() {
        axios.get<JobApplicationTracker[]>("/api/JobApplication/dash").then(
            (response) => {
                setOdata(response.data)
            }
        )
    }

    const applicationCards = odata.map((application) => (
        <div className="JobapplicationCard" key={application.application.id}>
            <JobApplicationCard JobApplication={application}/>
        </div>));

    useEffect(() => {
        getApplications()

    }, []);
    return (
        <div>
            {applicationCards}
        </div>
    )
}
