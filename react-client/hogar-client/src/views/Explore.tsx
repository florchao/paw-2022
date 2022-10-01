
import EmployeeCard from "../components/EmployeeCard";
import FilterForm from "../components/FilterForm";

export const Explore = () => {
    return (
        <div className="grid grid-cols-7">
            <FilterForm/>
            <EmployeeCard/>
        </div>
    )
}

export default Explore;