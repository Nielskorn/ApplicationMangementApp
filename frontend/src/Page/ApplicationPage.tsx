import {useEffect, useState} from "react";
import {Application} from "../types/Application.ts";
import axios from "axios";
import ApplicationGallery from "../Component/ApplicationGallary.tsx";
import JobApplicationGallary from "../Component/JobApplicationGallary.tsx";
import ReactSwitch from "react-switch";


export function ApplicationPage() {
    const [data, setData] = useState<Application[]>([]);


    function fetchData() {
        axios.get("/api/application")
            .then(response => {
                setData(response.data)
            })
            .catch(error => {
                console.log(error)
            });
    }

    const [isWithJobOffer, setIsWithJobOffer] = useState<boolean>(false)
    useEffect(() => {
        fetchData();
        setIsWithJobOffer(false);
    }, []);

    function onChange() {
        setIsWithJobOffer(!isWithJobOffer);

    }

    return (
        <div>
            <label className="switch-label"> Switch Application
                <ReactSwitch title="switch Application" checked={isWithJobOffer} onChange={onChange}/>
            </label>
            {isWithJobOffer ? (<ApplicationGallery applications={data}/>) : (<JobApplicationGallary/>)}
        </div>)
}
