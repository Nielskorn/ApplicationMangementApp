import {useNavigate, useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import axios from "axios";
import {JobOffer} from "../types/JobOffer.ts";

export default function JobDetailsPage() {
    const {id} = useParams<{ id: string }>();
    const navigate = useNavigate()
    const [companyLogo, setCompanyLogo] = useState<string>()
    const [companyName, setCompanyName] = useState<string>();
    const [jobTitle, setJobTitle] = useState<string>();
    const [jobDesc, setJobDesc] = useState<string>();
    const [location, setLocation] = useState<string>();


    function fetchJobDetais() {
        axios.get<JobOffer>(`/api/job-offer/${id}`).then(
            (response) => {
                setJobTitle(response.data.jobTitle)
                setJobDesc(response.data.jobDescription)
                setCompanyName(response.data.companyName)
                setCompanyLogo(response.data.Url_companyLogo)
                setLocation(response.data.location)
            })

    }

    function navigateToEdit() {
        navigate(`/editJobOffer/${id}`)

    }

    function deleteJob() {
        const isConfirmed = window.confirm("Are you sure you want to delete this job offer?")
        if (isConfirmed) {
            axios.delete(`/api/job-offer/${id}`).then(() => {
                alert("deleted Successfuly");
                navigate("/jobOffer")
            })
                .catch(error => console.error("error beim löschne" + error))
        }
    }

    useEffect(() => {
        fetchJobDetais()
    }, []);

    return (
        <div className="JobDetails">
            <img src={companyLogo} alt={companyName}/>
            <h2>{jobTitle}</h2>
            <p>{jobDesc}</p>
            <br/>

            <h3>Company: {companyName}</h3>
            <p>In {location}</p>
            <button onClick={navigateToEdit}>Edit</button>
            <button onClick={deleteJob}>delete</button>
        </div>
    )
}
