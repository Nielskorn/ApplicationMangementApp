import axios from "axios";
import {ChangeEvent, FormEvent, useEffect, useState} from "react";
import {useParams} from "react-router-dom";


export default function NewApplicationSite() {
    const {id} = useParams<{ id: string }>();
    const [jobOfferID, setJobOfferID] = useState<string>("");
    const [resume, setResume] = useState<File | null>(null);


    const [coverLetter, setCoverLetter] = useState<File | null>(null);

    const [reminderDate, setReminderDate] = useState<string>("");

    function OnSubmit(event: FormEvent<HTMLFormElement>) {
        event.preventDefault();
        const data: FormData = new FormData()
        if (resume) {
            data.append("resume", resume)
            if (coverLetter) {
                data.append("coverLetter", coverLetter)
            }
            data.append("meta", new Blob([JSON.stringify({
                "jobOfferID": jobOfferID,
                "reminderDate": reminderDate
            })], {"type": "application/json"}))
            axios.post("/api/application", data, {headers: {"Content-Type": "multipart/form-data"}})
                .then()
                .catch(error => {
                    console.log(error)
                })
            OnReset()
        } else {
            alert("a resume is needed for the Application to be Vaild")
        }


    }


    useEffect(() => {
        if (id) {
            setJobOfferID(id)
        }
    }, []);

    function OnReset() {
        setJobOfferID("")
        setCoverLetter(null)
        setResume(null)
        setReminderDate("null")
    }

    function handelFileChange(event: ChangeEvent<HTMLInputElement>) {
        if (event.target.files) {
            setResume(event.target.files[0])
        }
    }

    function handelCoverletterChange(event: ChangeEvent<HTMLInputElement>) {
        if (event.target.files) {
            setCoverLetter(event.target.files[0])
        }
    }


    return (


        <div>
            <form className="addForm" onSubmit={OnSubmit} onReset={OnReset}>
                <label>JobOfferId:
                    <input type="text"
                           value={jobOfferID}
                           placeholder="Job OfferId"
                           className="formField"
                           onChange={event => {
                               setJobOfferID(event.target.value)
                           }}
                    />
                </label>


                <label>resume:
                    <input type="file" name="Resume" onChange={handelFileChange}/>
                    {
                        resume && (<section>
                            filedetails:
                            <ul>
                                <li>
                                    Name:{resume.name}
                                </li>
                                <li>
                                    type:{resume.type}
                                </li>
                                <li>
                                    Size:{resume.size}
                                </li>

                            </ul>
                        </section>)
                    }
                </label>
                <label> coverLetter :
                    <input type="file" name="coverLetter" onChange={handelCoverletterChange}/>
                    {
                        coverLetter && (<section>
                            filedetails:
                            <ul>
                                <li>
                                    Name:{coverLetter.name}
                                </li>
                                <li>
                                    type:{coverLetter.type}
                                </li>
                                <li>
                                    Size:{coverLetter.size}
                                </li>

                            </ul>
                        </section>)
                    }
                    <input type={"datetime-local"} onChange={event => {
                        setReminderDate(event.target.value)
                    }}/>
                    {/* <DatePicker selected={reminderDate} onSelect={(date)=>setReminderDate(date!)} ></DatePicker>*/}
                </label>


                <button type={"submit"}>create Application</button>
                <button type={"reset"}>reset Form</button>
            </form>
        </div>
    )
}
