import {useEffect, useState} from "react";
import {Application} from "../types/Application.ts";
import axios from "axios";
import ApplicationGallery from "../Component/ApplicationGallary.tsx";

export function ApplicationPage(){
    const [data,setData]=useState<Application[]>([]);
    function fetchData(){
        axios.get("/api/application")
            .then(response=>
            {setData(response.data)})
            .catch(error=>
            {console.log(error)});
    }
    useEffect(()=>{fetchData();},[]);
    return(
        <ApplicationGallery applications={data}/>)

}