import {useParams} from "react-router-dom";
import {FormEvent, useEffect, useState} from "react";
import axios from "axios";

export default function EditJobOfferPage(){
    const {id}=useParams<{id:string}>();
    const [Url_companyLogo,setUrl_companyLogo]=useState<string>();
    const [companyName,setCompanyName]=useState<string>();
    const [location,setLocation]=useState<string>();
    const [jobTitle,setJobTitle]=useState<string>();
    const [jobDescription,setJobDescription]=useState<string>();
    useEffect(() => {

    }, []);
    function OnSumit(event:FormEvent<HTMLFormElement>){
        event.preventDefault();
        axios.put("/api/",{id,}).then().catch(error=>console.error(error))
        OnReset()
    }
    function  OnReset(){
        setUrl_companyLogo("")
        setLocation("")
        setCompanyName("")
        setJobTitle("")
        setJobDescription("")
    }
    return(<div>
        <form className="editForm" onSubmit={OnSumit}>
            <label> CompanyLogo:
            <input
            type={"text"}
            value={Url_companyLogo}
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
            <input type={"text"} value={jobDescription} placeholder="jobDescription"
            onChange={event =>setJobDescription(event.target.value)}/>
            </label>
        </form>
    </div>)
}