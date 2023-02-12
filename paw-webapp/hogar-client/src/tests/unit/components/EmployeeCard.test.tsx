import '@testing-library/jest-dom/extend-expect'
import { render} from '@testing-library/react'
import {BrowserRouter} from "react-router-dom";
import EmployeeCard from "../../../components/EmployeeCard";

const mockedUsedNavigate = jest.fn();

jest.mock('react-router-dom', () => ({
    ...jest.requireActual('react-router-dom'),
    useNavigate: () => mockedUsedNavigate,
}));

test('renders content', () => {

    const employeeCardTest = {
        "abilitiesArr": [
            "Cooking",
            "Pet Care"
        ],
        "availabilityArr": [
            "Part Time"
        ],
        "experienceYears": 3,
        "hourlyFee": 2,
        "id": 2,
        "image": "http://localhost:8080/images/2",
        "location": "GBA West",
        "name": "Sol Konfe",
        "rating": 3.5,
        "ratings": "http://localhost:8080/ratings/2",
        "reviews": "http://localhost:8080/employees/2/reviews",
        "self": "http://localhost:8080/employees/2",
        "users": "http://localhost:8080/users/2"
    }


    const component = render(
        <EmployeeCard employee={employeeCardTest}/>, {wrapper: BrowserRouter}
    )
    expect(component.container).toHaveTextContent(employeeCardTest.name);
    expect(component.container).toHaveTextContent(employeeCardTest.location)
})