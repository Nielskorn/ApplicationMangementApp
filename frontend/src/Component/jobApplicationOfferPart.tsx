import {JobOffer} from "../types/JobOffer.ts";
import {useNavigate} from "react-router-dom";

export default function JobApplicationOfferPart(jobOffer: Readonly<JobOffer>) {
    const navigate = useNavigate();

    function navigateToJobOfferEdit() {
        navigate(`/editJobOffer/` + jobOffer.id)

    }

    function navigateToJobOfferDetails() {
        navigate("/joboffer/" + jobOffer.id)
    }


    return (<div className="jobOfferPart">
        <h3>JobOffer</h3>
        <img src={jobOffer.Url_companyLogo} alt="logo"/>
        <p>{jobOffer.companyName}</p>
        <p> {jobOffer.jobTitle}</p>
        <p>{jobOffer.location}</p>
        <p>{jobOffer.jobDescription}</p>
        <button onClick={navigateToJobOfferEdit}>Edit Job offer</button>
        <button onClick={navigateToJobOfferDetails}>go to Details</button>
        
    </div>)
}