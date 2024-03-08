import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import { Button } from "react-bootstrap";
import AccountCircleRoundedIcon from "@material-ui/icons/AccountCircleRounded";
import { ReactComponent as UnimpactLogo } from "../../resources/unimpact_logo.svg"; 

interface TNavPath {
    pathName: string;
    url: string;
}

const HomeNavBar: React.FC = () => {
    const paths: TNavPath[] = [
        {
            pathName: "Início",
            url: "#home_page_main_content",
        },
        {
            pathName: "Sobre o unimpact",
            url: "#section-1",
        },
        {
            pathName: "Objetivos",
            url: "#section-2",
        },
        {
            pathName: "Notícias e Vídeos",
            url: "#section-3", 
        },
    ];

    const [activePath, setActivePath] = useState<string | null>(null);

    useEffect(() => {
        const handleScroll = () => {
            const scrollY = window.scrollY;

            const activePath = paths.find((path) => {
                const targetElement = document.querySelector(path.url);
                if (!targetElement) return false;
                const rect = targetElement.getBoundingClientRect();
                return rect.top <= 100 && rect.bottom >= 100;
            });

            setActivePath(activePath ? activePath.url : null);
        };

        window.addEventListener("scroll", handleScroll);

        return () => {
            window.removeEventListener("scroll", handleScroll);
        };
    }, [paths]);

    const smoothScrollTo = (target: string) => {
        const targetElement = document.querySelector(target);
        if (targetElement) {
            targetElement.scrollIntoView({
                behavior: "smooth",
            });
        }
    };

    return (
        <nav className="navbar">
            <div className="nav-items">
                <a href="/">
                    <UnimpactLogo />
                </a>
                {paths.map((path, index) => (
                    <a
                        key={index}
                        href="#"
                        onClick={() => smoothScrollTo(path.url)}
                        className={path.url === activePath ? "active" : ""}
                    >
                        {path.pathName}
                    </a>
                ))}
            </div>
            <div className="nav-options">
                <Link to="/cadastro">
                    <Button
                        variant="light me-3"
                        className="sign_method btn-light shadow-box-element rounded-element pe-3 ps-3"
                    >
                        Cadastro
                    </Button>
                </Link>
                <Link to="/entrar">
                    <Button
                        className="sign_method rounded-element shadow-box-element"
                    >
                        <AccountCircleRoundedIcon className="me-2" /> Login
                    </Button>
                </Link>
            </div>
        </nav>
    );
};

export default HomeNavBar;

