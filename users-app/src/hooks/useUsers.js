import { useNavigate } from "react-router-dom";
import Swal from "sweetalert2";
import { findAll, remove } from "../services/userService";
import { useDispatch, useSelector } from "react-redux";
import { removeUser, loadingUsers, onUserSelectedForm, onOpenForm, onCloseForm, initialUserForm, loadingError } from "../store/slices/users/usersSlice";
import { useAuth } from "../auth/hooks/useAuth";
import { startAddUser } from "../store/slices/users/thunks";

export const useUsers = () => {
    const { users, userSelected, visibleForm, errors, isLoading } = useSelector( state=> state.users );
    const dispatch = useDispatch();

    const navigate = useNavigate();
    
    const { login, handlerLogout } = useAuth();

    const getUsers = async () => {
        try {
        const result = await findAll();
        // console.log(result.data);
        dispatch(loadingUsers(result.data));
        } catch(error){
            if (error.response?.status == 401){
                handlerLogout();
            }
        }
    }
    const handlerAddUser = (user) => {
        if (!login.isAdmin) return;
        dispatch(startAddUser(user))
        navigate('/users');
    }

    const handlerRemoveUser = (id) => {
        // console.log(id);
        if (!login.isAdmin) return;
        Swal.fire({
            title: 'Esta seguro que desea eliminar?',
            text: "Cuidado el usuario sera eliminado!",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Si, eliminar!'
        }).then(async (result) => {
            if (result.isConfirmed) {

                try {
                    await remove(id);
                    dispatch(removeUser(id))
                    Swal.fire(
                        'Usuario Eliminado!',
                        'El usuario ha sido eliminado con exito!',
                        'success'
                    )
                } catch (error) {
                    if (error.response?.status == 401){
                        handlerLogout();
                    }
                }

            }
        })

    }

    const handlerUserSelectedForm = (user) => {
        dispatch(onUserSelectedForm({ ...user }))
    }

    const handlerOpenForm = () => {
        dispatch(onOpenForm());
    }

    const handlerCloseForm = () => {
        dispatch(loadingError({}));
        dispatch(onCloseForm());
    }
    return {
        users,
        userSelected,
        initialUserForm,
        visibleForm,
        errors,
        isLoading,
        handlerAddUser,
        handlerRemoveUser,
        handlerUserSelectedForm,
        handlerOpenForm,
        handlerCloseForm,
        getUsers,
    }
}