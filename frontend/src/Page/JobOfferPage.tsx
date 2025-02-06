import axios from "axios";
import {useEffect, useState} from "react";
import {JobOffer} from "../types/JobOffer.ts";
import JobOfferGallery from "../Component/JobOfferGallary.tsx";

export default function JobOfferPage(){
    const [jobOffers,setJobOffers]=useState<JobOffer[]>([])
    function fetchJobOffers(){
        axios.get("api/joboffer").
        then(response=>{setJobOffers(response.data)}).
            catch(error=>console.error(error) )
    }

    useEffect(() => {
     fetchJobOffers();
    }, []);
    return(<JobOfferGallery jobOffers={jobOffers}/>);
}