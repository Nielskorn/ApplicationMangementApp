import {useParams} from "react-router-dom";
import {FormEvent, useEffect, useState} from "react";
import axios from "axios";
import {JobOffer} from "../types/JobOffer.ts";

export default function EditJobOfferPage(){
    const {id}=useParams<{id:string}>();
    const [url_companyLogo, setUrl_companyLogo]=useState<string>("");
    const [companyName,setCompanyName]=useState<string>("");
    const [location,setLocation]=useState<string>("");
    const [jobTitle,setJobTitle]=useState<string>("");
    const [jobDescription,setJobDescription]=useState<string>("");
    const [linkJobAd, setLinkJobAd]=useState<string>("");
    function fetchJob(){
        axios.get<JobOffer>(`/api/job-offer/${id}`).
        then((response)=>{
            setJobTitle(response.data.jobTitle);
            setJobDescription(response.data.jobDescription)
            setLocation(response.data.location);
            setUrl_companyLogo(response.data.Url_companyLogo)
            setCompanyName(response.data.companyName)
            setLinkJobAd(response.data.LinkJobAd)
        })
    }
    useEffect(() => {
    fetchJob()
    }, []);
    function OnSumit(event:FormEvent<HTMLFormElement>){
        event.preventDefault();
        axios.put("/api/job-offer/"+id,
            { Url_companyLogo: url_companyLogo, companyName:companyName,
                location:location, jobTitle: jobTitle, jobDescription: jobDescription,LinkJobAd:linkJobAd
        }).then().catch(error=>console.error(error))
        OnReset()
    }
    function  OnReset(){
        setUrl_companyLogo("")
        setLocation("")
        setCompanyName("")
        setJobTitle("")
        setJobDescription("")
        setLinkJobAd("")
    }
    return(<div>
        <form className="editForm" onSubmit={OnSumit}>
            <label> CompanyLogo:
            <input
            type={"text"}
            value={url_companyLogo}
            placeholder="CompanyLogoURL"
            onChange={event =>
                setUrl_companyLogo(event.target.value)}

            />

            </label>

            <label>CompanyName:
                <input
                type={"text"}
                value={companyName}
                placeholder="CompanyName"
                onChange={event => setCompanyName(event.target.value)}
                />

            </label>
            <label>location:
                <input type={"text"} value={location} placeholder="location" onChange={event => setLocation(event.target.value)}/>
            </label>
            <label>jobtitle:
            <input type={"text"} value={jobTitle} placeholder="JobTitle"
            onChange={event => setJobTitle(event.target.value) }/>
            </label>
            <label>job Description:
                {/*<input type={"text"} value={jobDescription} placeholder="jobDescription"
            onChange={event =>setJobDescription(event.target.value)}/>*/}
                <textarea name="textareaJobDesc" rows={4} cols={40}  value={jobDescription} onChange={event =>setJobDescription(event.target.value)} />
            </label>
            <label>Job ad link:
                <input type={"text"}
                       value={linkJobAd}
                       placeholder="job ad link"
                       onChange={event =>setLinkJobAd(event.target.value)}
                />
            </label>
            <button type={"submit"}>Save Changes</button>
            <button type={"reset"}>reset Form</button>
        </form>
    </div>)
}