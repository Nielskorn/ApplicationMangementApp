import {JobOffer} from "../types/JobOffer.ts";
import JobOfferCard from "./JobOfferCard.tsx";

type jobOfferGalleryProps={
    jobOffers:JobOffer[]
}
export default function JobOfferGallery({jobOffers}:Readonly<jobOfferGalleryProps>){
    const jobOfferCards=jobOffers.map((offer:JobOffer)=>(<div className="jobOfferCard" key={offer.id}>
        <JobOfferCard jobOffer={offer}/>
    </div>))
    return(<>
        {jobOfferCards}</>)
}
