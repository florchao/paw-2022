import { EmployeeService } from "../service/EmployeeService"

export const FilterForm = () => {
    return (
        <div>
            {/* <h2 onClick={EmployeeService.getFilteredEmployees()}>Buenas</h2> */}
            <h2 onClick={async () =>  {
                await EmployeeService.getFilteredEmployees()
            }}>Buenas</h2>


        </div>
    )
}

export default FilterForm