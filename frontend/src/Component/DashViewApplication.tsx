import axios from "axios";

import {useEffect, useState} from "react";
import "/src/App.css"

import {JobApplicationTracker} from "../types/JobApplicationTracker.ts";
import JobApplicationCard from "./JobApplicationCard.tsx";

export function DashViewApplication(){
    const [odata,setOdata]=useState<JobApplicationTracker[]>([])
    function getApplications(){
        axios.get<JobApplicationTracker[]>("/api/JobApplication").then(
            (response)=>{
                setOdata(response.data)
            }
        )
    }
    
    function filterApplicationByReminderDate(){
      setOdata (odata.sort((a,b)=>Date.parse( a.application.reminderTime) - Date.parse( b.application.reminderTime)).slice(0,3));
    }

    const applicationCards=odata.map((application) =>(<div className="JobapplicationCard" key={application.application.id}>
        <JobApplicationCard JobApplication={application}/>
    </div>));

        useEffect(() => {
        getApplications()
        filterApplicationByReminderDate()
    }, []);
        return(
        <div >
            {applicationCards}
        </div>
        )
    }