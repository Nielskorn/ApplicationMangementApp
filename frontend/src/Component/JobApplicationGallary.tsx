import {useEffect, useState} from "react";
import {JobApplicationTracker} from "../types/JobApplicationTracker.ts";
import axios from "axios";
import JobApplicationCard from "./JobApplicationCard.tsx";

export default function JobApplicationGallary() {
    const [odata, setOdata] = useState<JobApplicationTracker[]>([])

    function getApplications() {
        axios.get<JobApplicationTracker[]>("/api/JobApplication/all").then(
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
