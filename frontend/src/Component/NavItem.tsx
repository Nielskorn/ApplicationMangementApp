import {NavLink} from "react-router-dom";

interface NavItemProps {
    label: string;
    href: string;
    icon?: string;

}

export const NavItem: React.FC<NavItemProps> = ({label, icon, href}) => (
    <NavLink to={href} className={'nav-item'
    }>
        {icon && <span className="icon">{icon}</span>}
        <span>{label}</span>
    </NavLink>
);
