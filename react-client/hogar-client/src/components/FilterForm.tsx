import {EmployeeService} from "../service/EmployeeService"

export const FilterForm = () => {
    return (
        <div>
            {/*<h2 onClick={async () => {*/}
            {/*    await EmployeeService.getFilteredEmployees()*/}
            {/*}}>Buenas</h2>*/}
            <div className = "bg-purple-300 mr-8 p-6 rounded-2xl mt-2 shadow-xl border-solid border-violet-500 border-2 h-fit w-fit">
                <h1 className="font-semibold text-violet-900">Reset filters_</h1>
                <div className={'flex flex-col items-center'}>
                    <h1 className="font-semibold">Minimum years of experience_</h1>
                    <input className={'rounded-md'}/>
                    <h1 className="font-semibold">Name_</h1>
                    <input className={'rounded-md'}/>
                    <h1 className="font-semibold">Location_</h1>
                    <input className={'rounded-md'}/>
                    {/*TODO: Esta mal hecho el width de los filters!!!*/}
                    <h1 className="font-semibold">Abilities_</h1>
                    <div className={'grid grid-cols-12 w-3/4'}>
                        <div className={'col-span-3 col-start-1]'}>
                            <h1>Iron_</h1>
                        </div>
                        <div className={'col-start-11'}>
                            <input type={'checkbox'}></input>
                        </div>
                        <div className={'col-span-3 col-start-1'}>
                            <h1>Cook_</h1>
                        </div>
                        <div className={'col-start-11'}>
                            <input type={'checkbox'}></input>
                        </div>
                    </div>
                    <h1 className="font-semibold">Availability_</h1>
                    <div className={'grid grid-cols-12 w-3/4'}>
                        <div className={'col-span-3 col-start-1]'}>
                            <h1>Part_</h1>
                        </div>
                        <div className={'col-start-11'}>
                            <input type={'checkbox'}></input>
                        </div>
                        <div className={'col-span-3 col-start-1'}>
                            <h1>Full_</h1>
                        </div>
                        <div className={'col-start-11'}>
                            <input type={'checkbox'}></input>
                        </div>
                    </div>
                    <input value={'Filter'} type="submit" className="mt-4 border shadow-md text-lg w-5/6 focus:outline-none text-violet-900 bg-purple-400 border border-purple-900 hover:bg-yellow-300 hover:bg-opacity-50 font-small rounded-lg text-sm px-5 py-2.5" />
                </div>
            </div>

        </div>
    )
}

export default FilterForm
