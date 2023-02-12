import '@testing-library/jest-dom/extend-expect'
import { render} from '@testing-library/react'
import JobCard from "../../../components/JobCard";
import {BrowserRouter} from "react-router-dom";

const mockedUsedNavigate = jest.fn();

jest.mock('react-router-dom', () => ({
    ...jest.requireActual('react-router-dom'),
    useNavigate: () => mockedUsedNavigate,
}));

test('renders content', () => {

    const jobCardTest = {
        employerId: {id: 1,
            name: "Employer Employer",
            reviews: "http://localhost:8080/employers/1/reviews"},
        jobId: 1,
        status: 0,
        opened: true,
        title: "\"MORENO\"",
        location: "GBA West",
        description: "\"HOLA\"",
        experienceYears: 10,
        availability: ["Part Time", "Full Time"],
        abilities:["Cooking", "Iron"],
        applicants:"http://localhost:8080/applicants"
    }


    const component = render(
        <JobCard job={jobCardTest}/>, {wrapper: BrowserRouter}
    )
    expect(component.container).toHaveTextContent(jobCardTest.title);
    expect(component.container).toHaveTextContent(jobCardTest.description);
    expect(component.container).toHaveTextContent(jobCardTest.location)

})