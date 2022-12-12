import {EmployeeService} from "../service/EmployeeService"
import React, {useState} from "react";
import {JobService} from "../service/JobService";
import {useTranslation} from "react-i18next";

export const FilterForm = ({setList, type}: { setList: React.Dispatch<React.SetStateAction<object>>, type: string }) => {

    const [minimumYears, setMinimumYears] = useState(0);
    const [name, setName] = useState()
    const [location, setLocation] = useState()

    const [abilities, setAbilities] = useState([])
    const [availability, setAvailability] = useState([])

    const {t} = useTranslation();


    const resetHandler = async (): Promise<any> => {
        if(type === "employee") {
            const allEmployees = await EmployeeService.getEmployees()
            // setName();
            // setLocation('')
            setMinimumYears(0)
            setAbilities([])
            setAvailability([])
            setList(allEmployees)
        } else if (type === "jobs") {
            const allJobs = await JobService.getJobs()
            // setName();
            // setLocation('')
            setMinimumYears(0)
            setAbilities([])
            setAvailability([])
            setList(allJobs)
        }
    }

    function abilitiesHandler(num: number): void {
        let abilitiesArr: any = abilities;
        (abilitiesArr.includes(num)) ? (abilitiesArr = abilitiesArr.filter((x: number) => x !== num)) : abilitiesArr.push(num)
        setAbilities(abilitiesArr)
    }

    function availabilityHandler(num: number): void {
        let availabilityArr: any = availability;
        (availabilityArr.includes(num)) ? (availabilityArr = availabilityArr.filter((x: number) => x !== num)) : availabilityArr.push(num)
        setAvailability(availabilityArr)
    }

    const handleFilter = async (): Promise<any> => {
        if(type === "employee") {
            const employees = await EmployeeService.getFilteredEmployees(
                minimumYears,
                name,
                location,
                (abilities.toString() === "") ? undefined : abilities.toString().toString(),
                (availability.toString() === "") ? undefined : availability.toString().toString()
            )
            setList(employees)
        } else if (type === "jobs") {
            const jobs = await JobService.getFilteredJobs(
                minimumYears,
                name,
                location,
                (abilities.toString() === "") ? undefined : abilities.toString().toString(),
                (availability.toString() === "") ? undefined : availability.toString().toString()
            )
            setList(jobs)
        }
    }

    return (
        <div>
            <div
                className="bg-purple-300 mr-8 p-6 rounded-2xl mt-2 shadow-xl border-solid border-violet-500 border-2 h-fit w-full">
                <h1 className="font-semibold text-violet-900 hover:cursor-pointer" onClick={resetHandler}>{t('FilterForm.reset')}</h1>
                <div className={'flex flex-col items-center'}>
                    <h1 className="font-semibold mt-2">{t('FilterForm.expYears')}</h1>
                    <input
                        value={minimumYears}
                        onChange={(e: any) => setMinimumYears(e.target.value)}
                        className={'rounded-md'}
                        type={"number"}
                        required
                        min={0}
                        max={100}
                        onInput={e => (e.target as HTMLInputElement).setCustomValidity("")}
                        onInvalid={e => (e.target as HTMLInputElement).setCustomValidity(t('FilterForm.expYearsError'))}
                    />
                    {(minimumYears < 0 || minimumYears > 100) &&
                        <p className="block mb-2 text-sm font-medium text-red-700 margin-top: 1.25rem">{t('FilterForm.expYearsError')}</p>
                    }
                    {/*TODO: Esta mal hecho el width de los filters!!!*/}
                    <h1 className="font-semibold mt-2">{t('FilterForm.name')}</h1>
                    <input
                        value={name}
                        type={"text"}
                        onChange={(e: any) => setName(e.target.value)}
                        className={'rounded-md'}
                        maxLength={100}
                        onInput={e => (e.target as HTMLInputElement).setCustomValidity("")}
                        onInvalid={e => (e.target as HTMLInputElement).setCustomValidity(t('FilterForm.nameError'))}
                    />
                    <h1 className="font-semibold mt-2">{t('FilterForm.location')}</h1>
                    <input
                        className={'rounded-md'}
                        value={location}
                        type={"text"}
                        maxLength={100}
                        onInput={e => (e.target as HTMLInputElement).setCustomValidity("")}
                        onInvalid={e => (e.target as HTMLInputElement).setCustomValidity(t('FilterForm.locationError'))}
                        onChange={(e: any) => setLocation(e.target.value)}/>
                    <h1 className="font-semibold mt-2">{t('FilterForm.abilities')}</h1>
                    <div className={'grid grid-cols-12 w-3/4 gap-x-1 items-center'}>
                        <div className={'col-span-10 col-start-1]'}>
                            <h1>{t('Abilities.cook')}</h1>
                        </div>
                        <div className={'col-start-11'}>
                            <input type={'checkbox'}  onChange={() => abilitiesHandler(1)}></input>
                        </div>
                        <div className={'col-span-10 col-start-1]'}>
                            <h1>{t('Abilities.iron')}</h1>
                        </div>
                        <div className={'col-start-11'}>
                            <input type={'checkbox'}  onChange={() => abilitiesHandler(2)}></input>
                        </div>
                        <div className={'col-span-10 col-start-1]'}>
                            <h1>{t('Abilities.child')}</h1>
                        </div>
                        <div className={'col-start-11'}>
                            <input type={'checkbox'}  onChange={() => abilitiesHandler(3)}></input>
                        </div>
                        <div className={'col-span-10 col-start-1'}>
                            <h1>{t('Abilities.older')}</h1>
                        </div>
                        <div className={'col-start-11'}>
                            <input type={'checkbox'}  onChange={() => abilitiesHandler(4)}></input>
                        </div>
                        <div className={'col-span-10 col-start-1]'}>
                            <h1>{t('Abilities.specialNeeds')}</h1>
                        </div>
                        <div className={'col-start-11'}>
                            <input type={'checkbox'}  onChange={() => abilitiesHandler(5)}></input>
                        </div>
                        <div className={'col-span-10 col-start-1]'}>
                            <h1>{t('Abilities.pets')}</h1>
                        </div>
                        <div className={'col-start-11'}>
                            <input type={'checkbox'} checked={abilities[5]} onChange={() => abilitiesHandler(6)}></input>
                        </div>
                    </div>
                    <h1 className="font-semibold mt-2">{t('FilterForm.availability')}</h1>
                    <div className={'grid grid-cols-12 w-3/4 gap-x-1'}>
                        <div className={'col-span-10 col-start-1'}>
                            <h1>{t('Availabilities.half')}</h1>
                        </div>
                        <div className={'col-start-11'}>
                            <input type={'checkbox'} checked={availability[0]} onChange={() => availabilityHandler(1)}></input>
                        </div>
                        <div className={'col-span-10 col-start-1'}>
                            <h1>{t('Availabilities.complete')}</h1>
                        </div>
                        <div className={'col-start-11'}>
                            <input type={'checkbox'} checked={availability[1]} onChange={() => availabilityHandler(2)}></input>
                        </div>
                        <div className={'col-span-10 col-start-1]'}>
                            <h1>{t('Availabilities.bed')}</h1>
                        </div>
                        <div className={'col-start-11'}>
                            <input type={'checkbox'} checked={availability[2]} onChange={() => availabilityHandler(3)}></input>
                        </div>
                    </div>
                    <input value={'Filter'} type="submit"
                           className="mt-4 border shadow-md text-lg w-5/6 focus:outline-none text-violet-900 bg-purple-400 border border-purple-900 hover:bg-yellow-300 hover:bg-opacity-50 font-small rounded-lg text-sm px-5 py-2.5 hover:cursor-pointer"
                           onClick={handleFilter}
                    />
                </div>
            </div>
        </div>
    )
}

export default FilterForm
