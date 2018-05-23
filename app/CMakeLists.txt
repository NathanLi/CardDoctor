
cmake_minimum_required(VERSION 3.4.1)



set(distribution_DIR ../../../../libs )
set(INC_DIR src/main/cpp/include)

#添加头文件目录
include_directories(src/main/cpp/include)

set(SOURCE_FILES src/main/cpp/compress.cpp)

add_library(compress SHARED
            ${SOURCE_FILES})

find_library(log-lib log)
find_library(graphics jnigraphics)

add_library(libjpeg SHARED IMPORTED)
set_target_properties(libjpeg PROPERTIES IMPORTED_LOCATION ${distribution_DIR}/${ANDROID_ABI}/libjpeg.so)



target_link_libraries(compress libjpeg ${log-lib} ${graphics})

