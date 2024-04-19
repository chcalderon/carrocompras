import Swal from "sweetalert2";
import { save, update } from "../../../services/userService";
import { addUser, loadingError, onCloseForm, updateUser } from "./usersSlice";

export const startAddUser = (user) => {

    return async (dispatch) => {
        //    console.log(user);
        try {
            if (user.id === 0) {
                const response = await save(user);
                dispatch(addUser(response.data));
            } else {
                const response = await update(user);
                dispatch(updateUser(response.data))
            }

            Swal.fire(
                (user.id === 0) ?
                    'Usuario Creado' :
                    'Usuario Actualizado',
                (user.id === 0) ?
                    'El usuario ha sido creado con exito!' :
                    'El usuario ha sido actualizado con exito!',
                'success'
            );
            dispatch(loadingError({}));
            dispatch(onCloseForm());

        } catch (error) {
            console.log(error);
            if (error.response && error.response.status == 400) {
                dispatch(loadingError(error.response.data));
            } else if (error.response && error.response.status == 500 &&
                error.response.data?.message?.includes('constraint')) {
                if (error.response.data?.message?.includes('UK_r43af9ap4edm43mmtq01oddj6')) {
                    dispatch(loadingError({ username: 'El nombre de usuario ya existe' }));
                }
                else if (error.response.data?.message?.includes('UK_6dotkott2kjsp8vw4d0m25fb7')) {
                    dispatch(loadingError({ email: 'El correo ya esta registrado' }));
                }
            } else if (error.response?.status == 401) {
                handlerLogout();
            } else {
                throw error;
            };
        }
    }
}