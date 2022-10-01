<<<<<<< HEAD

import EmployeeCard from "../components/EmployeeCard";
import FilterForm from "../components/FilterForm";

export const Explore = () => {
    return (
        <div className="grid grid-cols-7">
            <FilterForm/>
            <EmployeeCard/>
        </div>
    )
=======
import EmployeeCard from "../components/EmployeeCard";

export const Explore = () => {

    return (
        <EmployeeCard/>
    )

>>>>>>> master
}

export default Explore;