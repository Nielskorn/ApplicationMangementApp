import {NavItem} from "./NavItem.tsx";

export default function Header() {
    return (
        <nav className="navbar">
            <NavItem label="Home" href={"/"} icon="🏠"/>
            <NavItem href={"/jobOffer"} label="Job Offer" icon="💼"/>
            <NavItem label="new Job Offer" icon="🆕" href={"/newJobOffer"}/>
            <NavItem label="Application" icon="📄" href={"/application"}/>
            <NavItem label="new Application" icon="🆕" href={"newApplication"}/>
        </nav>

    )
}
