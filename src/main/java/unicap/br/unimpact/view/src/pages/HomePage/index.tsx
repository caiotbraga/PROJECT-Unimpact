import React from "react";

import { Card, Carousel } from "react-bootstrap";

import SchoolOutlined from "@material-ui/icons/SchoolOutlined";
import AccountBalanceOutlined from "@material-ui/icons/AccountBalanceOutlined";
import DomainOutlined from "@material-ui/icons/DomainOutlined";
import GroupOutlined from "@material-ui/icons/GroupOutlined";

import img1carousel from "../../resources/icons/recife1.jpg"
import img2carousel from "../../resources/icons/recife2.jpg"
import img3carousel from "../../resources/icons/recife3.jpg"
import unicaplogo from "../../resources/icons/unicaplogo.png"

import { ReactComponent as PlayIcon } from "../../resources/icons/white_play.svg";
import { ReactComponent as LeftControlIcon } from "../../resources/icons/left_control_carousel.svg";
import { ReactComponent as RightControlIcon } from "../../resources/icons/right_control_carousel.svg";
import { purple } from "@material-ui/core/colors";

interface props {}

const HomePage: React.FC<props> = () => {
    return (
        <>
            <main id="home_page_main_content" className="w-100">
                <HomePageCarousel />
            </main>
            <AboutUNIMPACTSection />
            <UNIMPACTGoalsSection />
            <UNIMPACTMediaSection />
            <Footer/>

        </>
    );
};

const Footer = () => {
    return (
    <div className="d-flex align-items-center justify-content-center main-item-bg" style={{ minHeight: "20%", backgroundColor:"#690013"}}>
    <img
        src={unicaplogo}
        alt="logo"
        style={{ display: "block", margin: "auto", height:"15%", width: "15%"}}
    />
    </div> 
    );
};

const UNIMPACTMediaSection = () => {
    return (
        <section id="section-3" className="w-100 home_page_section">
            <div className="row max-width-content mx-auto">
                <div id="__news" className="col">
                    <div className="__col_header row align-items-center">
                        <p className="main-text col main-text main-color">NOTÍCIAS</p>
                        <a href="/noticias" className="col-md-auto">
                            VEJA MAIS
                        </a>
                    </div>
                    <div className="row">
                        <a href="/news" className="col no-text-decoration" style={{textDecoration: 'none'}}>
                            <Card className="shadow-box-element">
                                <Card.Img
                                    variant="top"
                                    src="https://images.unsplash.com/photo-1559733709-d0122b67de73?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1470&q=80"
                                />
                                <Card.Body>
                                    <Card.Title>Card Title</Card.Title>
                                    <Card.Text>
                                        Some quick example text to build on the card title and make up the bulk of the
                                        card's content.
                                    </Card.Text>
                                </Card.Body>
                            </Card>
                        </a>
                        <a href="/news" className="col no-text-decoration" style={{textDecoration: 'none'}}>
                            <Card className="shadow-box-element">
                                <Card.Img
                                    variant="top"
                                    src="https://images.unsplash.com/photo-1559733709-d0122b67de73?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1470&q=80"
                                />
                                <Card.Body>
                                    <Card.Title>Card Title</Card.Title>
                                    <Card.Text>
                                        Some quick example text to build on the card title and make up the bulk of the
                                        card's content.
                                    </Card.Text>
                                </Card.Body>
                            </Card>
                        </a>
                        <div className="w-100"></div>
                        <a href="/news" className="col no-text-decoration" style={{textDecoration: 'none'}}>
                            <Card className="shadow-box-element">
                                <Card.Img
                                    variant="top"
                                    src="https://images.unsplash.com/photo-1559733709-d0122b67de73?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1470&q=80"
                                />
                                <Card.Body>
                                    <Card.Title>Card Title</Card.Title>
                                    <Card.Text>
                                        Some quick example text to build on the card title and make up the bulk of the
                                        card's content.
                                    </Card.Text>
                                </Card.Body>
                            </Card>
                        </a>
                        <a href="/news" className="col no-text-decoration" style={{textDecoration: 'none'}}>
                            <Card className="shadow-box-element">
                                <Card.Img
                                    variant="top"
                                    src="https://images.unsplash.com/photo-1559733709-d0122b67de73?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1470&q=80"
                                />
                                <Card.Body>
                                    <Card.Title>Card Title</Card.Title>
                                    <Card.Text>
                                        Some quick example text to build on the card title and make up the bulk of the
                                        card's content.
                                    </Card.Text>
                                </Card.Body>
                            </Card>
                        </a>
                    </div>
                </div>
                <div className="col">
                    <div className="__col_header row align-items-center">
                        <p className="main-text main-color col">VÍDEOS</p>
                        <a href="/noticias" className="col-md-auto">
                            VEJA MAIS
                        </a>
                    </div>
                    <div className="">
                        <Card className="shadow-box-element">
                            <div
                                className="card_image_background"
                                style={{
                                    backgroundImage:
                                        "url('https://www.eatright.org/-/media/eatrightimages/health/weightloss/yourhealthandyourweight/promote-positive-body-image-kids-839295596.jpg')",
                                }}
                            >
                                <div className="background-dark-overlay">
                                    <PlayIcon />
                                </div>
                            </div>
                            <Card.Body>
                                <Card.Title>Card Title</Card.Title>
                                <Card.Text>
                                    Some quick example text to build on the card title and make up the bulk of the
                                    card's content.
                                </Card.Text>
                            </Card.Body>
                        </Card>
                        <Card className="shadow-box-element">
                            <div
                                className="card_image_background"
                                style={{
                                    backgroundImage:
                                        "url('https://www.eatright.org/-/media/eatrightimages/health/weightloss/yourhealthandyourweight/promote-positive-body-image-kids-839295596.jpg')",
                                }}
                            >
                                <div className="background-dark-overlay">
                                    <PlayIcon />
                                </div>
                            </div>
                            <Card.Body>
                                <Card.Title>Card Title</Card.Title>
                                <Card.Text>
                                    Some quick example text to build on the card title and make up the bulk of the
                                    card's content.
                                </Card.Text>
                            </Card.Body>
                        </Card>
                    </div>
                </div>
            </div>
        </section>
    );
};

interface goalProps {
    isAlignedRight: boolean;
    goalId: number;
    icon: JSX.Element;
    goalText: string;
}

const UNIMPACTGoalsSection = () => {
    const Goal: React.FC<goalProps> = ({ isAlignedRight, goalId, icon, goalText }) => (
        <div className={`__goal w-100 d-flex align-items-center ${isAlignedRight ? "justify-content-end" : ""} mb-4`}>
            {isAlignedRight ? <h1 className="main-text">{goalId}</h1> : icon}
            <p className={`mb-0 ${isAlignedRight ? "text-end" : ""}`}>{goalText}</p>
            {!isAlignedRight ? <h1 className="main-text">{goalId}</h1> : icon}
        </div>
    );

    return (
        <section id="section-2" className="w-100 home_page_section">
            <div className=" mx-auto">
                <div id="__section_title" className="d-flex align-items-center">
                    <PlayIcon />
                    <h3 className="main-text">Nossos objetivos</h3>
                </div>
                <div id="__goals" className="w-100">
                    <Goal
                        isAlignedRight={false}
                        goalId={1}
                        icon={<SchoolOutlined className="__goal_icon" />}
                        goalText={"Colaborar com a formação integral dos alunos e integração ao mercado de trabalho"}
                    />
                    <Goal
                        isAlignedRight={true}
                        goalId={2}
                        icon={<AccountBalanceOutlined className="__goal_icon" />}
                        goalText={
                            "Possibilitar às Instituições de Ensino Superior práticas inovadoras de ensino, pesquisa e extensão"
                        }
                    />
                    <Goal
                        isAlignedRight={false}
                        goalId={3}
                        icon={<GroupOutlined className="__goal_icon" />}
                        goalText={
                            "Colaborar com o desenvolvimento integral da sociedade por meio de uma rede de atores especializados em projetos e ações de impacto"
                        }
                    />
                    <Goal
                        isAlignedRight={true}
                        goalId={4}
                        icon={<DomainOutlined className="__goal_icon" />}
                        goalText={
                            "Contribuir com Instituições que queiram desenvolver projetos de impacto socioambiental"
                        }
                    />
                </div>
            </div>
        </section>
    );
};

const AboutUNIMPACTSection = () => {
    return (
        <section id="section-1" className="main-item-bg w-100 home_page_section">
            <div className="row max-width-content mx-auto">
                <div
                    id="floating-left-image"
                    className="col-md-auto"
                    style={{
                        backgroundImage: `url('https://www.eatright.org/-/media/eatrightimages/health/weightloss/yourhealthandyourweight/promote-positive-body-image-kids-839295596.jpg')`,
                    }}
                ></div>
                <div id="image-caption" className="col">
                    <h5 className="main-text">SOBRE O UNIMPACT</h5>
                    <p className="text-justify">
                        O Lorem Ipsum é um texto modelo da indústria tipográfica e de impressão. O Lorem Ipsum tem vindo
                        a ser o texto padrão usado por estas indústrias desde o ano de 1500, quando uma misturou os
                        caracteres de um texto para criar um espécime de livro. Este texto não só sobreviveu 5 séculos,
                        mas também o salto para a tipografia electrónica, mantendo-se essencialmente inalterada.
                    </p>
                    <p className="text-justify">
                        Lorem, ipsum dolor sit amet consectetur adipisicing elit. Inventore dolores sint voluptatum
                        aliquam ut aspernatur odio voluptate commodi, cum animi dolore tenetur dignissimos minima rem in
                        aut ea obcaecati incidunt.
                    </p>
                </div>
            </div>
        </section>
    );
};

const HomePageCarousel = () => {
    return (
        <Carousel
            id="home_page_carousel"
            className="mx-auto"
            pause={"hover"}
            prevIcon={<LeftControlIcon />}
            nextIcon={<RightControlIcon />}
        >
            <Carousel.Item>
                <img
                    className="d-block w-100"
                    src={img1carousel}
                    alt="First slide"
                />
            </Carousel.Item>
            <Carousel.Item>
                <img
                    className="d-block w-100"
                    src={img2carousel}
                    alt="Second slide"
                />
            </Carousel.Item>
            <Carousel.Item>
                <img
                    className="d-block w-100"
                    src={img3carousel}
                    alt="Third slide"
                />
            </Carousel.Item>
        </Carousel>
    );
};

export default HomePage;
