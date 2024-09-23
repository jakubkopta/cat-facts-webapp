import React from "react";
import './Card.css';

interface CardProps {
    author: string;
    fact: string;
}

const Card: React.FC<CardProps> = ({ author, fact }) => {
    return (
        <div className="card">
            <h2 className="author">By: {author}</h2>
            <p className="fact">{fact}</p>
        </div>
    );
};

export default Card;
