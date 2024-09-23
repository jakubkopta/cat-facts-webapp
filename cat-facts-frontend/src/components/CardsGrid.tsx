import Card from './Card';
import './CardsGrid.css';
import {useEffect, useRef, useState} from "react";

interface CatFact {
    user: string;
    fact: string;
}

const CardsGrid = () => {

    const indexRef = useRef(0);
    const [displayedFacts, setDisplayedFacts] = useState<CatFact[]>([
        { user: 'Loading...', fact: 'Please wait for cat facts...' },
        { user: 'Loading...', fact: 'Please wait for cat facts...' },
        { user: 'Loading...', fact: 'Please wait for cat facts...' },
        { user: 'Loading...', fact: 'Please wait for cat facts...' },
        { user: 'Loading...', fact: 'Please wait for cat facts...' },
        { user: 'Loading...', fact: 'Please wait for cat facts...' }
    ]);

    useEffect(() => {
        const eventSource = new EventSource('http://localhost:8080/cat-facts');

        eventSource.onmessage = (event) => {

            const newFact: CatFact = JSON.parse(event.data);

            setDisplayedFacts((prevFacts) => {
                const updatedFacts = [...prevFacts];
                updatedFacts[indexRef.current] = newFact;
                return updatedFacts;
            });

            indexRef.current = (indexRef.current + 1) % 6;

        };

        return () => {
            eventSource.close();
        };
    }, []);

    return (
        <>
            <h1 className="title">Cat Facts</h1>
            <div className="grid" >
                {displayedFacts.map((fact, index) => (
                    <Card key={index} author={fact.user} fact={fact.fact}/>
                ))}
            </div>
        </>
    )
};

export default CardsGrid;
