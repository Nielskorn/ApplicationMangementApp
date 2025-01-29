import {FormEvent, useState} from "react";
import axios from "axios";

export default function NewJobOfferPage(){
    const [companyName,setCompanyName]=useState<string>("")
    const [location,setLocation]=useState<string>("")
    const [jobTitle,setJobTitle]=useState<string>("")
    const [jobDescription,setJobDescription]=useState<string>("")
    const [companyLogoUrl,setCompanyLogoUrl]=useState<string>("")
function OnReset(){
        setCompanyName("")
    setJobTitle("")
    setJobDescription("")
    setLocation("")
    setCompanyLogoUrl("")

}
    function OnSubmit(event:FormEvent<HTMLFormElement>){
        event.preventDefault();
        axios.post("api/joboffer",{
            Url_companyLogo:companyLogoUrl
            ,companyName: companyName,
            location: location, jobTitle: jobTitle, jobDescription: jobDescription}).then().catch(error=>{console.error(error)})
        OnReset()
    }
    return(
        <div>
            <form className="addJobOfferForm" onSubmit={OnSubmit} onReset={OnReset}>
                <input type={"text"}value={companyLogoUrl}placeholder="companyLogoUrl" onChange={(event)=>setCompanyLogoUrl(event.target.value)} />
                <input type={"text"}
                       value={companyName}
                       placeholder="CompanyName"
                       onChange={(event)=>
                           setCompanyName(event.target.value)}
                />
                <input type={"text"}
                       value={location}
                       placeholder="Location"
                       onChange={(event)=>
                           setLocation(event.target.value)}
                />
                <input type={"text"}
                       value={jobTitle}
                       placeholder="jobTitle"
                       onChange={(event)=>
                           setJobTitle(event.target.value)}
                />
                <input type={"text"}
                       value={jobDescription}
                       placeholder="jobDescription"
                       onChange={(event)=>
                           setJobDescription(event.target.value)}
                />
                <button type={"submit"}>create Job offer</button>
                <button type={"reset"}>reset Form</button>
            </form>

        </div>
    )
}