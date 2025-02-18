import {useEffect, useState} from "react";
import {JobApplicationTracker} from "../types/JobApplicationTracker.ts";
import axios from "axios";

import {Link, useNavigate, useParams} from "react-router-dom";
import StatusIndicator from "../Component/StatusIndicator.tsx";

import JobApplicationOfferPart from "../Component/jobApplicationOfferPart.tsx";

export default function JobApplicationDetails() {
    const [data, setData] = useState<JobApplicationTracker>()
    const {id} = useParams<{ id: string }>();


    function fetchJobApplication() {
        axios.get(`/api/JobApplication/${id}`).then(
            response => {
                setData(response.data)

            }
        )
    }

    useEffect(() => {
        fetchJobApplication()
    }, []);
    const navigate = useNavigate();

    function navigateToAppplicationEditpage() {
        navigate("/editApplication/" + id);
    }


    function navigateToApplicationDetailspage() {
        navigate("/application/" + id);
    }


    if (data !== null && data !== undefined) {
        return (<>
                {data.jobOffer ? (<h2>{data.jobOffer.jobTitle}</h2>) : (<h2>{data.application.id}</h2>)}
                <div className="JobapplicationDetailsCard">
                    {data.jobOffer && (<JobApplicationOfferPart id={data.jobOffer.id}
                                                                jobDescription={data.jobOffer.jobDescription}
                                                                jobTitle={data.jobOffer.jobTitle}
                                                                location={data.jobOffer.location}
                                                                Url_companyLogo={data.jobOffer.Url_companyLogo}
                                                                LinkJobAd={data.jobOffer.LinkJobAd}
                                                                companyName={data.jobOffer.companyName}/>)}

                    <div className="applicationPart">
                        <h3>Application</h3>
                        <Link to={data.application.resume} target='_blank'>resume</Link>
                        <br/>
                        {data.application.coverLetter ? (
                            <Link to={data.application.coverLetter} target='_blank'>cover letter </Link>) : null
                        }
                        <StatusIndicator status={data.application.applicationStatus}></StatusIndicator>
                        <p>{data.application.dateOfCreation.toString()}</p>
                        {data.application.reminderTime !== null &&
                            <p>{data.application.reminderTime}</p>
                        }
                        <button onClick={navigateToAppplicationEditpage}>Edit Application</button>
                        <button onClick={navigateToApplicationDetailspage}>go to Details</button>


                    </div>
                </div>
            </>

        )

    }
}
