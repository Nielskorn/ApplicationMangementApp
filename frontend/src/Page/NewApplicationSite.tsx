import axios from "axios";
import {FormEvent, useState} from "react";


export default function NewApplicationSite(){
   const [jobOfferID,setJobOfferID]=useState<string>("");

   const [resume,setResume]=useState<string>("");
   const [coverLetter,setCoverLetter]=useState<string>("");
   const [reminderDate,setReminderDate]=useState<string>("");
    function OnSubmit(event:FormEvent<HTMLFormElement>){
        event.preventDefault();
        axios.post("/api/application",{
            jobOfferID:  jobOfferID, resume:resume,
            coverLetter: coverLetter, reminderTime: reminderDate
        })
            .then()
            .catch(error=>
        {console.log(error)})
        OnReset()
    }
    function OnReset(){
        setJobOfferID("")
        setCoverLetter("")
        setResume("")
        setReminderDate("")
    }
    return(


        <div>
            <form className="addForm" onSubmit={OnSubmit} onReset={OnReset}>
                <label>JobOfferId:
                <input type="text"
                       value={jobOfferID}
                       placeholder="Job OfferId"
                       className="formField"
                       onChange={event => {setJobOfferID(event.target.value)}}
                />
                </label>


                <label>resume:
                <input type="text"
                       value={resume}
                       placeholder="resume"
                       className="formField"
                       onChange={event => {setResume(event.target.value)}}
                />
                </label>
                <label> coverLetter
                <input type="text"
                       value={coverLetter}
                       placeholder="coverLetter"
                       className="formField"
                       onChange={event => {setCoverLetter(event.target.value)}}
                />
                    <input type={"datetime-local"} onChange={event => {setReminderDate(event.target.value)}}/>
                    {/* <DatePicker selected={reminderDate} onSelect={(date)=>setReminderDate(date!)} ></DatePicker>*/}
                </label>
                <button type={"submit"}>create Application</button>
                <button type={"reset"}>reset Form</button>
            </form>
        </div>
    )
}