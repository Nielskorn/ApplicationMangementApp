import {useNavigate} from "react-router-dom";
import {JobOffer} from "../types/JobOffer.ts";
type jobOfferCardProps={
    jobOffer:JobOffer
}
export default function JobOfferCard({jobOffer}:Readonly<jobOfferCardProps>){
    const navigate=useNavigate();
    function navigateToJobOfferDetails(){
        navigate("/joboffer/"+jobOffer.id)
    }
    return(
        <div onClick={navigateToJobOfferDetails}>
            <img src={jobOffer.Url_companyLogo} alt={jobOffer.companyName}/>
            <h2>{jobOffer.jobTitle}</h2>
            <h2>{jobOffer.companyName}</h2>
        </div>
    )
}