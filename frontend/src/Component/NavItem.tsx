interface NavItemProps {
    label: string;
    href: string;
    icon?: string;

}

export const NavItem: React.FC<NavItemProps> = ({label, icon, href}) => (
    <a href={href}
       className={'nav-item'
       }

    >
        {icon && <span className="icon">{icon}</span>}
        <span>{label}</span>
    </a>
);